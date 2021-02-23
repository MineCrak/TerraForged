/*
 * MIT License
 *
 * Copyright (c) 2021 TerraForged
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.terraforged.mod.feature.decorator.poisson;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.terraforged.engine.tile.chunk.ChunkReader;
import com.terraforged.mod.api.feature.decorator.DecorationContext;
import com.terraforged.mod.featuremanager.util.codec.Codecs;
import com.terraforged.noise.Module;
import com.terraforged.noise.Source;
import com.terraforged.noise.util.NoiseUtil;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.placement.IPlacementConfig;

public class FastPoissonConfig implements IPlacementConfig {

    public static final float DEFAULT_JITTER = 0.8F;

    public static final Codec<FastPoissonConfig> CODEC = Codecs.create(
            FastPoissonConfig::serialize,
            FastPoissonConfig::deserialize
    );

    public final int radius;
    public final float scale;
    public final float jitter;
    public final float biomeFade;
    public final float densityVariation;
    public final int densityVariationScale;

    public FastPoissonConfig(float scale, int radius, float biomeFade, int densityScale, float densityVariation) {
        this(scale, DEFAULT_JITTER, radius, biomeFade, densityScale, densityVariation);
    }

    public FastPoissonConfig(float scale, float jitter, int radius, float biomeFade, int densityScale, float densityVariation) {
        this.scale = scale;
        this.jitter = jitter;
        this.radius = radius;
        this.biomeFade = biomeFade;
        this.densityVariationScale = densityScale;
        this.densityVariation = NoiseUtil.clamp(densityVariation, 0, 1);
    }

    public DensityNoise getDensityNoise(int seed, DecorationContext context) {
        Module densityVariance = Source.ONE;
        BiomeVariance biomeVariance = BiomeVariance.NONE;

        if (this.biomeFade > BiomeVariance.MIN_FADE) {
            ChunkPos pos = context.getChunk().getPos();
            ChunkReader reader = context.getGenerator().getChunkReader(pos.x, pos.z);
            if (reader != null) {
                biomeVariance = new BiomeVariance(reader, this.biomeFade);
            }
        }

        if (this.densityVariation > 0) {
            densityVariance = Source.simplex(seed + 1, densityVariationScale, 1)
                    .scale(densityVariation)
                    .bias(1 - densityVariation);
        }

        return new DensityNoise(biomeVariance, densityVariance);
    }

    private static <T> Dynamic<T> serialize(FastPoissonConfig config, DynamicOps<T> ops) {
        ImmutableMap.Builder<T, T> builder = ImmutableMap.builder();
        builder.put(ops.createString("radius"), ops.createInt(config.radius));
        builder.put(ops.createString("scale"), ops.createFloat(config.scale));
        builder.put(ops.createString("jitter"), ops.createFloat(config.jitter));
        builder.put(ops.createString("biome_fade"), ops.createFloat(config.biomeFade));
        builder.put(ops.createString("density_scale"), ops.createInt(config.densityVariationScale));
        builder.put(ops.createString("density_variation"), ops.createFloat(config.densityVariation));
        return new Dynamic<>(ops, ops.createMap(builder.build()));
    }

    private static <T> FastPoissonConfig deserialize(Dynamic<T> dynamic) {
        int radius = dynamic.get("radius").asInt(4);
        float scale = dynamic.get("scale").asFloat(0.1F);
        float jitter = dynamic.get("jitter").asFloat(DEFAULT_JITTER);
        float biomeFade = dynamic.get("biome_fade").asFloat(0.2F);
        int densityScale = dynamic.get("density_scale").asInt(0);
        float densityVariation = dynamic.get("density_variation").asFloat(0F);
        return new FastPoissonConfig(scale, jitter, radius, biomeFade, densityScale, densityVariation);
    }
}
