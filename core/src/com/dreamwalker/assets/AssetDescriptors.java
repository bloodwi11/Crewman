package com.dreamwalker.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    public static final AssetDescriptor<TextureAtlas> GLASS_TILE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GLASS_TILE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> PLATE_TILE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.PLATE_TILE, TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> NULL_TILE =
            new AssetDescriptor<TextureAtlas>(AssetPaths.NULL_TILE, TextureAtlas.class);

    public static final AssetDescriptor<Texture> TILE_GRID =
            new AssetDescriptor<Texture>(AssetPaths.TILE_GRID, Texture.class);

    private AssetDescriptors() {
    }
}