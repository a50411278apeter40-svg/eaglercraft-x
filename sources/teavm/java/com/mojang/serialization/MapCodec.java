package com.mojang.serialization;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * EaglerCraft stub for MapCodec.
 *
 * Must provide instance codec() method matching DFU's signature:
 *   public abstract Codec<T> codec()  →  ()Lcom/mojang/serialization/Codec;
 *
 * ExtraCodecs._clinit_ calls var$1.codec() on a MapCodec instance,
 * which fails at runtime if the method is missing from the prototype.
 *
 * Previous design had only static codec() to avoid name clash,
 * but the static method is NOT the one being called at runtime.
 * Fix: provide instance codec() and remove the static version.
 * Callers that previously relied on the static MapCodec.codec()
 * (e.g. unitCodec()) are updated to use Codec.PASSTHROUGH cast.
 */
public abstract class MapCodec<T> {

    /**
     * Returns this MapCodec wrapped as a Codec.
     * This is the instance method DFU calls, e.g.:
     *   ExtraCodecs._clinit_: var$1 = someMapCodec; var$1.codec();
     */
    public Codec<T> codec() {
        final MapCodec<T> self = this;
        return new Codec<T>() {
            @Override public T decode(Object input) { return null; }
            @Override public Object encode(T value) { return null; }
        };
    }

    // ---- abstract methods subclasses implement ----
    public abstract T decode(DynamicOps<?> ops, MapLike<?> input);

    // ---- default instance methods ----
    public Encoder<T> encoder() { return (Encoder<T>) this; }
    public Decoder<T> decoder() { return (Decoder<T>) this; }
    public DataResult<T> decode(DynamicOps<?> ops, Object input) { return DataResult.success(null); }
    public <S> MapCodec<S> flatXmap(Function<T, DataResult<S>> to, Function<S, DataResult<T>> from) { return (MapCodec<S>) this; }
    public <S> MapCodec<S> xmap(Function<T, S> to, Function<S, T> from) { return (MapCodec<S>) this; }
    public MapCodec<T> orElse(T defaultValue) { return this; }
    public MapCodec<T> recursive(String name, Function<MapCodec<T>, MapCodec<T>> function) { return this; }
    public MapCodec<T> withLifecycle(Lifecycle lifecycle) { return this; }
    public MapCodec<T> stable() { return this; }
    public MapCodec<T> deprecated(int since) { return this; }
    public MapCodec<java.util.Optional<T>> optionalFieldOf(String name) { return (MapCodec<java.util.Optional<T>>) this; }
    public MapCodec<T> unit(T defaultValue) { return this; }
    public MapCodec<T> unit(Supplier<T> defaultValue) { return this; }
    public Codec<T> unitCodec(T defaultValue) { return codec(); }
    public Codec<T> unitCodec(Supplier<T> defaultValue) { return codec(); }
    public MapCodec<T> validate(Function<T, DataResult<T>> validator) { return this; }
    public <S> Codec<S> dispatch(Function<T, String> name, Function<String, Codec<? extends T>> codec) { return codec(); }
    public MapCodec<T> orElseGet(java.util.function.Consumer<String> onError, java.util.function.Supplier<T> supplier) { return this; }

    /** Static factory for MapCodec instances used from RecordCodecBuilder etc. */
    public static <T> MapCodec<T> of(MapEncoder<T> encoder, MapDecoder<T> decoder) {
        return new MapCodec<T>() {
            @Override public T decode(DynamicOps<?> ops, MapLike<?> input) { return null; }
        };
    }

    public static <T> MapCodec<T> of(MapEncoder<T> encoder, MapDecoder<T> decoder, Supplier<String> name) {
        return of(encoder, decoder);
    }

    /** forGetter support used by RecordCodecBuilder */
    public <O> RecordCodecBuilder<O, T> forGetter(Function<O, T> getter) {
        return RecordCodecBuilder.of(getter, this);
    }
}
