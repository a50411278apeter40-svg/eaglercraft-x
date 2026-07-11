#!/usr/bin/env python3
"""
Patch teavm-relocated-libs-asm-0.15.0.jar to accept class file version 70
(Java 26) in org.teavm.asm.ClassReader.

The version check is in ClassReader.<init>(byte[], int, int):
    bipush 69       ; push Opcodes.V25
    if_icmple +22   ; if version <= 69, skip throw

This script patches the `bipush 69` (0x10 0x45) to `bipush 127` (0x10 0x7F),
changing the upper bound from 69 to 127.
"""
import zipfile
import shutil
import os
import sys

JAR_PATH = sys.argv[1] if len(sys.argv) > 1 else '/tmp/teavm-asm.jar'
OUTPUT_PATH = sys.argv[2] if len(sys.argv) > 2 else '/tmp/teavm-asm-patched.jar'

print(f"Input JAR: {JAR_PATH}")
print(f"Output JAR: {OUTPUT_PATH}")

# List JAR contents first
with zipfile.ZipFile(JAR_PATH, 'r') as zin:
    names = zin.namelist()
    print(f"JAR has {len(names)} entries")
    cr_entries = [n for n in names if 'ClassReader' in n]
    print(f"ClassReader entries: {cr_entries}")
    
    if not cr_entries:
        print("ERROR: No ClassReader.class found in JAR!")
        print("All .class entries:")
        for n in names[:30]:
            print(f"  {n}")
        sys.exit(1)
    
    cr_key = cr_entries[0]
    cr_data = bytearray(zin.read(cr_key))

print(f"ClassReader.class ({cr_key}) size: {len(cr_data)} bytes")

# Find the version check pattern: bipush 69 (0x10 0x45) followed by if_icmple (0xA4)
target_pattern = bytes([0x10, 0x45, 0xA4])
patched_count = 0
for i in range(len(cr_data) - 2):
    if cr_data[i] == 0x10 and cr_data[i+1] == 0x45 and cr_data[i+2] == 0xA4:
        print(f"  Found version check at offset {i}: bipush 69 + if_icmple")
        cr_data[i+1] = 0x7F
        patched_count += 1
        print(f"  Patched: bipush 69 -> bipush 127 (0x10 0x7F)")

if patched_count == 0:
    print("WARNING: Could not find version check pattern (bipush 69 + if_icmple)")
    print("Searching for bipush 69 alone...")
    for i in range(len(cr_data) - 1):
        if cr_data[i] == 0x10 and cr_data[i+1] == 0x45:
            context = cr_data[i:i+5]
            print(f"  bipush 69 at offset {i}, context: {context.hex()}")
    print("Continuing without patch (JAR may already support version 70)")
else:
    print(f"Patched {patched_count} version check(s)")

# Write the patched JAR
shutil.copy(JAR_PATH, OUTPUT_PATH)
with zipfile.ZipFile(JAR_PATH, 'r') as zin:
    with zipfile.ZipFile(OUTPUT_PATH, 'w', zipfile.ZIP_DEFLATED) as zout:
        for item in zin.infolist():
            if item.filename == cr_key:
                zout.writestr(item, bytes(cr_data))
                print(f"Wrote patched {cr_key} ({len(cr_data)} bytes)")
            else:
                zout.writestr(item, zin.read(item.filename))

print(f"\nPatched JAR saved to {OUTPUT_PATH}")
