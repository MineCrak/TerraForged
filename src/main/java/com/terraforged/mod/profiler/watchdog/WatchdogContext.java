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

package com.terraforged.mod.profiler.watchdog;

import com.terraforged.engine.concurrent.cache.SafeCloseable;
import com.terraforged.mod.chunk.TFChunkGenerator;
import net.minecraft.world.chunk.IChunk;

public interface WatchdogContext extends SafeCloseable {

    WatchdogContext NONE = new WatchdogContext() {
        @Override
        public void check(long now) {

        }

        @Override
        public boolean set(IChunk chunk, TFChunkGenerator generator, long duration) {
            return false;
        }

        @Override
        public void pushPhase(String phase) {

        }

        @Override
        public void pushIdentifier(Object identifier, long time) {

        }

        @Override
        public void pushTime(String type, Object o, long time) {

        }

        @Override
        public void close() {

        }
    };

    void check(long now);

    boolean set(IChunk chunk, TFChunkGenerator generator, long duration);

    void pushPhase(String phase);

    void pushIdentifier(Object identifier, long time);

    void pushTime(String type, Object o, long time);
}
