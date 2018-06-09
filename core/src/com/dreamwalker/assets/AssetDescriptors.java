package com.dreamwalker.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    public static final AssetDescriptor<TextureAtlas> NULL_MENU =
            new AssetDescriptor<TextureAtlas>(AssetPaths.NULL_MENU, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> GUI_MENU_1 =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GUI_MENU_1, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> GUI_MENU_2 =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GUI_MENU_2, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> GLASS_TILE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GLASS_TILE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> PLATE_TILE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.PLATE_TILE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> NULL_TILE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.NULL_TILE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> MENU =
            new AssetDescriptor<TextureAtlas>(AssetPaths.MENU, TextureAtlas.class);

    public static final AssetDescriptor<Texture> DIRT =
            new AssetDescriptor<Texture>(AssetPaths.DIRT, Texture.class);

    public static final AssetDescriptor<Texture> ROAD =
            new AssetDescriptor<Texture>(AssetPaths.ROAD, Texture.class);

    public static final AssetDescriptor<Texture> SKULL =
            new AssetDescriptor<Texture>(AssetPaths.SKULL, Texture.class);

    public static final AssetDescriptor<Texture> TILE_GRID =
            new AssetDescriptor<Texture>(AssetPaths.TILE_GRID, Texture.class);

    public static final AssetDescriptor<Texture> GRASS =
            new AssetDescriptor<Texture>(AssetPaths.GRASS, Texture.class);

    private AssetDescriptors() {
    }
}