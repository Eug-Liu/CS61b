package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.io.Serializable;

public class Clean implements Serializable {
    TETile world[][];

    protected Clean (TETile World[][]){
        world = World;
    }

    TETile[] chopEffect = new TETile[3];
    int[] chopEffectPos = new int[6];

    protected void cleanEffect(){
        if (chopEffect[0] != null) {
            world[chopEffectPos[0]][chopEffectPos[1]] = extinguish(chopEffect[0]);
            world[chopEffectPos[2]][chopEffectPos[3]] = extinguish(chopEffect[1]);
            world[chopEffectPos[4]][chopEffectPos[5]] = extinguish(chopEffect[2]);
            chopEffect[0] = null;
        }
    }

    protected TETile extinguish(TETile img) {
        if (img.equals(Tileset.GRASS)) {
            return Tileset.SAND;
        }
        return img;
    }

}
