package com.ext.render;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DroidCardsView extends View {

   //图片与图片之间的间距
   private int mCardSpacing = 150;
   //图片与左侧距离的记录
   private int mCardLeft = 10;

   private List<DroidCard> mDroidCards = new ArrayList<DroidCard>();

   private Paint paint = new Paint();

   public DroidCardsView(Context context) {
      super(context);
      initCards();
   }

   public DroidCardsView(Context context, AttributeSet attrs) {
      super(context, attrs);
      initCards();
   }


   /**
    * 初始化卡片集合
    */
   protected void initCards() {
      Resources res = getResources();
      mDroidCards.add(new DroidCard(res, R.drawable.kathryn, mCardLeft));

      mCardLeft += mCardSpacing;
      mDroidCards.add(new DroidCard(res, R.drawable.alex, mCardLeft));

      mCardLeft += mCardSpacing;
      mDroidCards.add(new DroidCard(res, R.drawable.claire, mCardLeft));
   }

   @Override
   protected void onDraw(Canvas canvas) {
      super.onDraw(canvas);
      for (int i = 0; i < mDroidCards.size() - 1; i++) {
         drawDroidCard(canvas, mDroidCards, i);
      }

      drawLastDroidCard(canvas, mDroidCards.get(mDroidCards.size() - 1));
      invalidate();
   }

   /**
    * 绘制最后一个DroidCard
    *
    * @param canvas
    * @param c
    */
   private void drawLastDroidCard(Canvas canvas, DroidCard c) {
      canvas.drawBitmap(c.bitmap, c.x, 0f, paint);
   }

   /**
    * 绘制DroidCard
    *
    * @param canvas
    * @param mDroidCards
    * @param i
    */
   private void drawDroidCard(Canvas canvas, List<DroidCard> mDroidCards, int i) {
      DroidCard c = mDroidCards.get(i);
      // 保存当前Canvas绘图环境的所有属性
      canvas.save();
      // 将所有绘制的东西放在ctx.save()和ctx.restore()，能起到保存绘制状态和防止污染状态栈,养成一个良好的习惯
      // 裁剪画布 调用该方法后，只会显示被裁剪的区域，之外的区域将不会显示
      canvas.clipRect((float) c.x, 0f, (float) (mDroidCards.get(i + 1).x), (float) c.height);
      canvas.drawBitmap(c.bitmap, c.x, 0f, paint);
      // 恢复当前Canvas绘图环境的所有属性
      canvas.restore();
   }
}
