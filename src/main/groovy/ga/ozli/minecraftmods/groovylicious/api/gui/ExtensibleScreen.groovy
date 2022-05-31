package ga.ozli.minecraftmods.groovylicious.api.gui

import com.mojang.blaze3d.vertex.PoseStack
import ga.ozli.minecraftmods.groovylicious.api.StringUtils
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import groovy.transform.stc.ClosureParams
import net.minecraft.client.gui.Font
import net.minecraft.client.gui.components.Widget
import net.minecraft.client.gui.components.events.GuiEventListener
import net.minecraft.client.gui.narration.NarratableEntry
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextComponent
import net.minecraft.network.chat.TranslatableComponent
import org.intellij.lang.annotations.Language

import javax.management.openmbean.SimpleType

import static groovy.lang.Closure.DELEGATE_FIRST

@CompileStatic
class ExtensibleScreen extends Screen {

    final List<Closure> onPreInit = []
    final List<Closure> onInit = []
    final List<Closure> onPostInit = []

    final List<Closure> onPreRender = []
    final List<Closure> onRender = []
    final List<Closure> onPostRender = []

    boolean drawBackground = true

    ExtensibleScreen(final Component title) {
        super(title)
    }

    ExtensibleScreen(final String title) {
        super(StringUtils.stringToComponent(title))
    }

    // --- Screen overrides ---
    @Override
    void init() {
        if (!onPreInit.isEmpty()) onPreInit.each { it.call(screenInstance: this) }
        onInit.each { it.call(screenInstance: this) }
        if (!onPostInit.isEmpty()) onPostInit.each { it.call(screenInstance: this) }
    }

    @Override
    void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        if (!onPreRender.isEmpty()) onPreRender.each { it.call(screenInstance: this, poseStack: poseStack) }
        if (drawBackground) this.renderBackground(poseStack)
        onRender.each { it.call(screenInstance: this, poseStack: poseStack) }
        super.render(poseStack, mouseX, mouseY, partialTick)
        if (!onPostRender.isEmpty()) onPostRender.each {it.call(screenInstance: this, poseStack: poseStack) }
    }

    // --- Access widening ---
    @Override
    <T extends GuiEventListener & Widget & NarratableEntry> T addRenderableWidget(T widget) {
        return super.addRenderableWidget(widget)
    }

    @Override
    <T extends Widget> T addRenderableOnly(T widget) {
        return super.addRenderableOnly(widget)
    }

    @Override
    <T extends GuiEventListener & NarratableEntry> T addWidget(T listener) {
        return super.addWidget(listener)
    }

    Font getFont() {
        return super.font
    }

//    def addRenderableOnly(widget) {
//        return super.addRenderableOnly(widget)
//    }
//
//    def addWidget(listener) {
//        return super.addWidget(listener)
//    }
}

//                // alternative syntax 1
//                 size {
//                    width = 100
//                    height = 20
//                }
//
//                // alternative syntax 2
//                size = new Size(width: 100, height: 20)