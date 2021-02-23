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

package com.terraforged.mod.util.setup;

import com.terraforged.mod.Log;
import com.terraforged.mod.api.event.SetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SetupDebug {

    @SubscribeEvent
    public static void terrain(SetupEvent.Terrain event) {
        log(event);
    }

    @SubscribeEvent
    public static void surface(SetupEvent.Surface event) {
        log(event);
    }

    @SubscribeEvent
    public static void layers(SetupEvent.Layers event) {
        log(event);
    }

    @SubscribeEvent
    public static void geology(SetupEvent.Geology event) {
        log(event);
    }

    @SubscribeEvent
    public static void features(SetupEvent.Features event) {
        log(event);
    }

    @SubscribeEvent
    public static void columns(SetupEvent.Decorators event) {
        log(event);
    }

    private static void log(SetupEvent<?> event) {
        Log.debug("Setting up {}", event.getManager().getClass().getSimpleName());
    }
}
