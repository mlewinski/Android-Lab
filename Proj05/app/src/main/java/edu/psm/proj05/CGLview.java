package edu.psm.proj05;

/**
 * Created by Grzegorz on 2018-05-13.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGLConfig;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.view.MotionEvent;

public class CGLview extends GLSurfaceView {

	private float prevX;
	private float prevY;

	private final float TOUCH_SCALE = 0.5625f;
	private final float TRACK_SCALE = 36.0f;

    private CRenderer renderer;



	private class CRenderer implements Renderer{
		public float rotateX;
		public float rotateY;
		public float DIST = -6f;
		private int EXP_DELAY = 40;
		private CCube cube;

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			Bitmap tex[] = new Bitmap[3];
			tex[0] = BitmapFactory.decodeResource(getResources(), R.drawable.b1);
			tex[1] = BitmapFactory.decodeResource(getResources(), R.drawable.b2);
			tex[2] = BitmapFactory.decodeResource(getResources(), R.drawable.b3);
			int texId[] = new int[3];
			gl.glGenTextures(3, texId, 0);
			for(int i=0; i<3; i++){
				gl.glBindTexture(GL10.GL_TEXTURE_2D, texId[i]);
				GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, tex[i], 0);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D,
					GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
				gl.glTexParameterf(GL10.GL_TEXTURE_2D,
					GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
				gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
				tex[i].recycle();
			}
			gl.glClearColor(0.7f, 0.8f, 0.6f, 1.0f);
            gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glEnable(GL10.GL_DEPTH_TEST);
			gl.glEnable(GL10.GL_TEXTURE_2D);
			gl.glFrontFace(GL10.GL_CW);
			cube = new CCube(texId);
		}

        @Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			GLU.gluPerspective(gl, 45.0f, (float)width/(float)height, 0.1f, 100.0f);
		}
		@Override
		public void onDrawFrame(GL10 gl) {
			long start = System.currentTimeMillis();
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			// Nie zmieniamy położenia obserwatora: znajduje się
			// w punkcie (0,0,0) i patrzy w kierunku osi -Z
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();
			// Przesunięcie i obrót obiektu sześcianu
			gl.glTranslatef(0.0f, 0.0f, DIST);
            gl.glRotatef(rotateX, 0, 1, 0);
            gl.glRotatef(rotateY, 1, 0, 0);
			cube.draw(gl);

			long DELAY = System.currentTimeMillis()-start;
			if(DELAY< EXP_DELAY) try {
				Thread.sleep(EXP_DELAY -DELAY);
			} catch (InterruptedException e) {}
		}
	};

	public CGLview(Context context) {
        super(context);
        renderer = new CRenderer();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

	@Override
	public boolean onTrackballEvent(MotionEvent e) {
		renderer.rotateX += e.getX() * TRACK_SCALE;
		renderer.rotateY += e.getY() * TRACK_SCALE;
		requestRender();
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if(y<100){
					renderer.DIST += 0.5f*Math.signum(x - prevX);
					if(renderer.DIST>-2.5f)renderer.DIST = -2.5f;
					else if(renderer.DIST<-25.0f)renderer.DIST = -25.0f;
				}else{
					renderer.rotateX += (x- prevX) * TOUCH_SCALE;
					renderer.rotateY += (y- prevY) * TOUCH_SCALE;
				}
				requestRender();
				break;
			default: break;
		}
		prevX = x;
		prevY = y;
		return true;
	}
}
