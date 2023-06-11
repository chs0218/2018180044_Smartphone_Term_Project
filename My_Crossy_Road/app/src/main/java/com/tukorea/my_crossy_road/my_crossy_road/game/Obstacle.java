package com.tukorea.my_crossy_road.my_crossy_road.game;

import android.util.Log;

import com.tukorea.my_crossy_road.R;
import com.tukorea.my_crossy_road.framework.objects.Sprite;
import com.tukorea.my_crossy_road.framework.scene.BaseScene;

import java.util.Random;

public class Obstacle extends Sprite {
    private static final String TAG = Environment.class.getSimpleName();
    private Obstacle.Type type;
    private float fMoveSpeedX = 0.0f;
    public enum Type {
        Embulance, Tree, Rock, COUNT;
        int resId() {
            return resIds[this.ordinal()];
        }

        float width() {
            return widths[this.ordinal()];
        }

        float height() {
            return heights[this.ordinal()];
        }

        static int[] resIds = {
                R.mipmap.embulance,
                R.mipmap.tree,
                R.mipmap.grass,
        };
        static float[] widths = {2.0f, 1.5f, 1.5f};
        static float[] heights = {1.0f, 1.5f, 1.5f};
    }
    Obstacle(Environment.Type environmentType, float cx, float cy){
        if(environmentType == Environment.Type.Road)
        {
            Random random = new Random();

            type = Type.Embulance;
            fMoveSpeedX = random.nextFloat();

            this.x = cx + 1.0f;
            this.y = cy + 1.0f;
            this.width = Type.Embulance.width();
            this.height = Type.Embulance.height();
            setBitmapResource(R.mipmap.embulance);
        }
        else if(environmentType == Environment.Type.Grass)
        {
            Random random = new Random();

            int randInt = random.nextInt(2);
            //Log.d(TAG, "Random: " + randInt);

            if(randInt == 0)
            {
                type = Type.Tree;

                this.x = cx + 1.0f;
                this.y = cy + 1.0f;
                this.width = Type.Tree.width();
                this.height = Type.Tree.height();
                setBitmapResource(R.mipmap.tree);
            }
            else
            {
                type = Type.Rock;

                this.x = cx + 1.0f;
                this.y = cy + 1.0f;
                this.width = Type.Rock.width();
                this.height = Type.Rock.height();
                setBitmapResource(R.mipmap.rock);
            }
        }
        fixDstRect();
    }

    @Override
    public void update() {
        switch(type){
            case Rock:
                break;
            case Tree:
                break;
            case Embulance:
            {
                float dx = fMoveSpeedX * BaseScene.frameTime;

                x -= dx;
                dstRect.offset(-dx, 0);
            }
                break;
        }

        if (dstRect.top > 18.0f) {
            Log.d(TAG, "Removing:" + this);
            BaseScene.getTopScene().remove(getLayer(), this);
        }
    }

    public void PullDownObstacle(float speed)
    {
        float dy = speed * BaseScene.frameTime;

        y += dy;
        dstRect.offset(0, dy);
    }

    protected MainScene.Layer getLayer() {
        return MainScene.Layer.obstacle;
    }
}
