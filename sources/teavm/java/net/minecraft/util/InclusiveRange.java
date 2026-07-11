package net.minecraft.util;

/**
 * PATCHED: InclusiveRange stub for TeaVM browser environment.
 * Replaces the MC Record-based InclusiveRange.
 */
public final class InclusiveRange<T extends Comparable<T>> {
    private final T minInclusive;
    private final T maxInclusive;

    public InclusiveRange(T minInclusive, T maxInclusive) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
    }

    public T minInclusive() { return minInclusive; }
    public T maxInclusive() { return maxInclusive; }

    public boolean isValueInRange(T value) {
        return value.compareTo(minInclusive) >= 0 && value.compareTo(maxInclusive) <= 0;
    }

    @Override
    public String toString() {
        return "[" + minInclusive + ", " + maxInclusive + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InclusiveRange)) return false;
        InclusiveRange<?> r = (InclusiveRange<?>) o;
        return minInclusive.equals(r.minInclusive) && maxInclusive.equals(r.maxInclusive);
    }

    @Override
    public int hashCode() {
        return 31 * minInclusive.hashCode() + maxInclusive.hashCode();
    }
}
