package com.mojang.serialization;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * EaglerCraft stub for MapCodec — kept as interface so TeaVM emits
 * all default methods onto the prototype.
 *
 * Key fix: provide a default codec() instance method matching DFU:
 *   ()Lcom/mojang/serialization/Codec;
 *
 * TeaVM DCE was removing the static-only codec() because no virtual
 * dispatch path existed. Keeping it as a default method on the
 * interface ensures it is emitted on every implementing object.
 *
 * The name clash between static and instance codec() is avoided by
 * simply not having a static codec() — callers that need a no-op
 * Codec can use Codec.PASSTHROUGH directly.
 */
public interface MapCodec<T> {

    // ---- core abstract method (matches DFU signature) ----
    DataResult<T> decode(DynamicOps<?> ops, MapLike<?> input);

    // ---- THE critical instance method that MC calls at runtime ----
    default Codec<T> codec() {
        return new Codec<T>() {
            @Override public T decode(Object input) { return null; }
            @Override public Object encode(T value) { return null; }
        };
    }

    // ---- other default instance methods ----
    default Encoder<T> encoder() { return (Encoder<T>) this; }
    default Decoder<T> decoder() { return (Decoder<T>) this; }
    default DataResult<T> decode(DynamicOps<?> ops, Object input) { return DataResult.success(null); }

    @SuppressWarnings("unchecked")
    default <S> MapCodec<S> flatXmap(Function<T, DataResult<S>> to, Function<S, DataResult<T>> from) { return (MapCodec<S>) this; }

    @SuppressWarnings("unchecked")
    default <S> MapCodec<S> xmap(Function<T, S> to, Function<S, T> from) { return (MapCodec<S>) this; }

    default MapCodec<T> orElse(T defaultValue) { return this; }
    default MapCodec<T> recursive(String name, Function<MapCodec<T>, MapCodec<T>> function) { return this; }
    default MapCodec<T> withLifecycle(Lifecycle lifecycle) { return this; }
    default MapCodec<T> stable() { return this; }
    default MapCodec<T> deprecated(int since) { return this; }

    @SuppressWarnings("unchecked")
    default MapCodec<java.util.Optional<T>> optionalFieldOf(String name) { return (MapCodec<java.util.Optional<T>>) this; }

    default MapCodec<T> unit(T defaultValue) {
        // Return a new MapCodec that always provides defaultValue
        // (do NOT return 'this' — caller may not be a MapCodec)
        return (ops, input) -> DataResult.success(defaultValue);
    }
    default MapCodec<T> unit(Supplier<T> defaultValue) {
        return (ops, input) -> DataResult.success(defaultValue.get());
    }
    default Codec<T> unitCodec(T defaultValue) {
        final T val = defaultValue;
        return new Codec<T>() {
            @Override public T decode(Object input) { return val; }
            @Override public Object encode(T value) { return null; }
        };
    }
    default Codec<T> unitCodec(Supplier<T> defaultValue) {
        final Supplier<T> sup = defaultValue;
        return new Codec<T>() {
            @Override public T decode(Object input) { return sup.get(); }
            @Override public Object encode(T value) { return null; }
        };
    }
    default MapCodec<T> validate(Function<T, DataResult<T>> validator) { return this; }

    @SuppressWarnings("unchecked")
    default <S> Codec<S> dispatch(Function<T, String> name, Function<String, Codec<? extends T>> codec) {
        return (Codec<S>) codec();
    }

    default MapCodec<T> orElseGet(java.util.function.Consumer<String> onError, java.util.function.Supplier<T> supplier) { return this; }

    // ---- static factories ----
    static <T> MapCodec<T> of(MapEncoder<T> encoder, MapDecoder<T> decoder) {
        return (ops, input) -> DataResult.success(null);
    }

    static <T> MapCodec<T> of(MapEncoder<T> encoder, MapDecoder<T> decoder, Supplier<String> name) {
        return of(encoder, decoder);
    }
}
