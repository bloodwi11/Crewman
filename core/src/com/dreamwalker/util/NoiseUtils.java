package com.dreamwalker.util;

import com.dreamwalker.config.GameConfig;

import java.util.Random;

public class NoiseUtils {

    private NoiseUtils() {}

    public static float[][] generateWhiteNoise(int width, int height) {
        Random random = new Random(GameConfig.SEED); // Seed 0 for testing
        float[][] noise = new float[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                noise[x][y] = random.nextFloat() % 1;
            }
        }
        return noise;
    }

    public static float[][] generateSmoothNoise(float[][] baseNoise, int octave) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][] smoothNoise = new float[width][height];

        int samplePeriod = 1 << octave; // Calculates  2 ^ k
        float sampleFrequency = 1.0f / samplePeriod;

        for (int x = 0; x < width; x++) {
            // Calculate the horizontal sampling indices
            int sample_x0 = (x / samplePeriod) * samplePeriod;
            int sample_x1 = (sample_x0 + samplePeriod) % width; // Wrap around
            float horizontal_blend = (x - sample_x0) * sampleFrequency;

            for (int y = 0; y < height; y++) {
                // Calculate the vertical sampling indices
                int sample_y0 = (y / samplePeriod) * samplePeriod;
                int sample_y1 = (sample_y0 + samplePeriod) % height; // Wrap around
                float vertical_blend = (y - sample_y0) * sampleFrequency;

                // Blend the top two corners
                float top = interpolate(baseNoise[sample_x0][sample_y0],
                        baseNoise[sample_x1][sample_y0], horizontal_blend);

                // Blend the bottom two corners
                float bottom = interpolate(baseNoise[sample_x0][sample_y1],
                        baseNoise[sample_x1][sample_y1], horizontal_blend);

                // Final blend
                smoothNoise[x][y] = interpolate(top, bottom, vertical_blend);
            }
        }
        return smoothNoise;
    }

    public static float[][] generatePerlinNoise(float[][] baseNoise, int octaveCount) {
        int width = baseNoise.length;
        int height = baseNoise[0].length;

        float[][][] smoothNoise = new float[octaveCount][][]; // An array of 2D arrays containing

        float persistence = 0.5f;

        // Generate smooth noise
        for (int i = 0; i < octaveCount; i++) {
            smoothNoise[i] = generateSmoothNoise(baseNoise, i);
        }

        float[][] perlinNoise = new float[width][height];
        float amplitude = 1.0f;
        float totalAmplitude = 0.0f;

        // Blend noise together
        for (int octave = octaveCount - 1; octave >= 0; octave--) {
            amplitude *= persistence;
            totalAmplitude += amplitude;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    perlinNoise[x][y] += smoothNoise[octave][x][y] * amplitude;
                }
            }
        }

        // Normalisation
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                perlinNoise[x][y] /= totalAmplitude;
            }
        }
        return perlinNoise;
    }

    private static float interpolate(float x, float y, float alpha) {
        return x * (1 - alpha) + alpha * y;
    }
}
