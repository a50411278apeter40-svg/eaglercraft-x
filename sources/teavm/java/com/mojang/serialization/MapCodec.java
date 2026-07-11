package com.mojang.serialization;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * EaglerCraft stub for MapCodec (abstract class).
 *
 * The real DFU MapCodec has an instance codec() method:
 *   ()Lcom/mojang/serialization/Codec;
 * MC code calls var$1.codec() at runtime (e.g. in ExtraCodecs._clinit_).
 * Without this method on the prototype the call crashes with
 * "var$1.$codec is not a function".
 */
public abstract class MapCodec<T> {

    /**
     * Returns this MapCodec wrapped as a Codec.
     * This is the instance method TeaVM must emit on the prototype.
     */
    @SuppressWarnings("unchecked")
    public Codec<T> codec() {
        return new Codec<T>() {
            @Override public T decode(Object input) { return null; }
            @Override public Object encode(T value) { return null; }
        };
    }

    // ---- abstract core ----
    public abstract DataResult<T> decode(DynamicOps<?> ops, MapLike<?> input);

    // ---- default instance methods ----
    public Encoder<T> encoder() { return (Encoder<T>) this; }
    public Decoder<T> decoder() { return (Decoder<T>) this; }
    public DataResult<T> decode(DynamicOps<?> ops, Object input) { return DataResult.success(null); }

    @SuppressWarnings("unchecked")
    public <S> MapCodec<S> flatXmap(Function<T, DataResult<S>> to, Function<S, DataResult<T>> from) { return (MapCodec<S>) this; }

    @SuppressWarnings("unchecked")
    public <S> MapCodec<S> xmap(Function<T, S> to, Function<S, T> from) { return (MapCodec<S>) this; }

    public MapCodec<T> orElse(T defaultValue) { return this; }
    public MapCodec<T> recursive(String name, Function<MapCodec<T>, MapCodec<T>> function) { return this; }
    public MapCodec<T> withLifecycle(Lifecycle lifecycle) { return this; }
    public MapCodec<T> stable() { return this; }
    public MapCodec<T> deprecated(int since) { return this; }

    @SuppressWarnings("unchecked")
    public MapCodec<java.util.Optional<T>> optionalFieldOf(String name) { return (MapCodec<java.util.Optional<T>>) this; }

    public MapCodec<T> unit(T defaultValue) { return this; }
    public MapCodec<T> unit(Supplier<T> defaultValue) { return this; }
    public Codec<T> unitCodec(T defaultValue) { return codec(); }
    public Codec<T> unitCodec(Supplier<T> defaultValue) { return codec(); }
    public MapCodec<T> validate(Function<T, DataResult<T>> validator) { return this; }

    @SuppressWarnings("unchecked")
    public <S> Codec<S> dispatch(Function<T, String> name, Function<String, Codec<? extends T>> codec) {
        return (Codec<S>) codec();
    }

    public MapCodec<T> orElseGet(java.util.function.Consumer<String> onError, java.util.function.Supplier<T> supplier) { return this; }

    // ---- static factories ----
    public static <T> MapCodec<T> of(MapEncoder<T> encoder, MapDecoder<T> decoder) {
        return new MapCodec<T>() {
            @Override public DataResult<T> decode(DynamicOps<?> ops, MapLike<?> input) { return DataResult.success(null); }
        };
    }

    public static <T> MapCodec<T> of(MapEncoder<T> encoder, MapDecoder<T> decoder, Supplier<String> name) {
        return of(encoder, decoder);
    }
}
