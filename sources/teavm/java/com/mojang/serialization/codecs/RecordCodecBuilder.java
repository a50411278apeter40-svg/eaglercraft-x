package com.mojang.serialization.codecs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import java.util.function.Function;

// PATCHED: Returns no-op Codec/MapCodec instances instead of null.

public class RecordCodecBuilder<O, F> {

    @SuppressWarnings("unchecked")
    public static <O> Codec<O> create(Function<?, ?> builder) {
        return (Codec<O>) Codec.PASSTHROUGH;
    }

    @SuppressWarnings("unchecked")
    public static <O> MapCodec<O> mapCodec(Function<?, ?> builder) {
        return emptyMapCodec();
    }

    @SuppressWarnings("unchecked")
    public static <O, F> RecordCodecBuilder<O, F> of(Function<O, F> getter, MapCodec<F> codec) {
        return new RecordCodecBuilder<>();
    }

    // Instance method (used from MapCodec.forGetter)
    public static <O, F> RecordCodecBuilder<O, F> instance() {
        return new RecordCodecBuilder<>();
    }

    /** Group support - returns a dummy MapCodec */
    @SuppressWarnings("unchecked")
    public static <O> MapCodec<O> group(Object... parts) {
        return emptyMapCodec();
    }

    @SuppressWarnings("unchecked")
    private static <T> MapCodec<T> emptyMapCodec() {
        return new MapCodec<T>() {
            @Override public DataResult<T> decode(DynamicOps<?> ops, MapLike<?> input) { return DataResult.success(null); }
        };
    }

    /** Inner Instance class for RecordCodecBuilder.create(instance -> ...) pattern */
    public static class Instance<O> {
        @SuppressWarnings("unchecked")
        public <A> App<RecordCodecBuilder<O, ?>, A> group(Object... parts) {
            return null;
        }
    }

    /** App marker interface */
    public interface App<F, A> {}
}
