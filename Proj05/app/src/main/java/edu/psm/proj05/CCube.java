package edu.psm.proj05;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lewin on 15.05.2018.
 */

public class CCube {
    private final int NODE_SIZE = 20;
    private final int TRG_ON_FACE = 2;
    private FloatBuffer[] buffer;
    private int[] texID;
    private float wezly[][] = {
            {   // ściana 1 (X Y -1 S T)
                    -1f, -1f, -1f, 0f, 0f,
                    -1f, 1f, -1f, 0f, 1f,
                    1f, -1f, -1f, 1f, 0f,
                    -1f, 1f, -1f, 0f, 1f,
                    1f, 1f, -1f, 1f, 1f,
                    1f, -1f, -1f, 1f, 0f,
                    // ściana 4 (X Y +1 S T)
                    -1f, -1f, 1f, 0f, 0f,
                    -1f, 1f, 1f, 0f, 1f,
                    1f, -1f, 1f, 1f, 0f,
                    -1f, 1f, 1f, 0f, 1f,
                    1f, 1f, 1f, 1f, 1f,
                    1f, -1f, 1f, 1f, 0f
            },
            {	//ściana 2 (-1 Y Z S T)
                    -1f, -1f, -1f, 0f, 0f,
                    -1f, -1f, 1f, 0f, 1f,
                    -1f, 1f, -1f, 1f, 0f,
                    -1f, 1f, -1f, 1f, 0f,
                    -1f, -1f, 1f, 0f, 1f,
                    -1f, 1f, 1f, 1f, 1f,
                    //ściana 5 (+1 Y Z S T)
                    1f, -1f, -1f, 0f, 0f,
                    1f, 1f, -1f, 1f, 0f,
                    1f, -1f, 1f, 0f, 1f,
                    1f, 1f, -1f, 1f, 0f,
                    1f, 1f, 1f, 1f, 1f,
                    1f, -1f, 1f, 0f, 1f
            },
            {   //ściana 3 (X -1 Z S T)
                    -1f, -1f, -1f, 0f, 0f,
                    1f, -1f, -1f, 1f, 0f,
                    -1f, -1f, 1f, 0f, 1f,
                    1f, -1f, -1f, 1f, 0f,
                    1f, -1f, 1f, 1f, 1f,
                    -1f, -1f, 1f, 0f, 1f,
                    //ściana 6 (X +1 Z S T)
                    -1f, 1f, -1f, 0f, 0f,
                    -1f, 1f, 1f, 0f, 1f,
                    1f, 1f, -1f, 1f, 0f,
                    1f, 1f, -1f, 1f, 0f,
                    -1f, 1f, 1f, 0f, 1f,
                    1f, 1f, 1f, 1f, 1f
            }
    };




    public CCube(int[] id) {
        buffer = new FloatBuffer[3];
        for (int i = 0; i < 3; i++) {
            ByteBuffer buf = ByteBuffer.allocateDirect(NODE_SIZE * 3 * TRG_ON_FACE * 2);
            buf.order(ByteOrder.nativeOrder());
            buffer[i] = buf.asFloatBuffer();
            buffer[i].put(wezly[i]);
            buffer[i].flip();
        }
        texID = new int[3];
        for (int i = 0; i < 3; i++) texID[i] = id[i];
    }

    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        for (int i = 0; i < 3; i++) {
            gl.glBindTexture(GL10.GL_TEXTURE_2D, texID[i]);
            buffer[i].position(0);
            gl.glVertexPointer(3, GL10.GL_FLOAT, NODE_SIZE, buffer[i]);
            buffer[i].position(3);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, NODE_SIZE, buffer[i]);
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3 * TRG_ON_FACE * 2);
        }
// zablokowanie edycji buforów po stronie klienta
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
}