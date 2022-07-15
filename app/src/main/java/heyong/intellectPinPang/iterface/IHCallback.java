package heyong.intellectPinPang.iterface;

import android.graphics.Canvas;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import heyong.intellectPinPang.adapter.game.RefereeCompetitionInterfaceAdapter;
import heyong.intellectPinPang.event.ToTargetEvent;


/**
 * @author :created by ${yangpf}
 * 时间:2018/7/6 15
 * 邮箱：xxx@.qq.com
 */
public class IHCallback extends ItemTouchHelper.Callback {
    public static final String TAG = IHCallback.class.getSimpleName();
    private RefereeCompetitionInterfaceAdapter moveAdapter;

    public IHCallback(RefereeCompetitionInterfaceAdapter moveAdapter) {
        this.moveAdapter = moveAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //允许上下的拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //只允许从右向左侧滑
        int swipeFlags = ItemTouchHelper.LEFT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        moveAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        Log.e(TAG, "onMove: local="+viewHolder.getAdapterPosition()+"===target==="+target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        moveAdapter.onItemDissmiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            //滑动时改变Item的透明度
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        int endPosition = viewHolder.getAdapterPosition();
        Log.e(TAG, "onMove: local=" + viewHolder.getAdapterPosition() + "===target===" + endPosition);

        EventBus.getDefault().post(new ToTargetEvent(moveAdapter.getData().get(viewHolder.getAdapterPosition()).getId(), (viewHolder.getAdapterPosition() + 1)));

    }
}
