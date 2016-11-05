package work.vuong.template.common.util.diff;

import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by oberon (vuongpham) on 28/10/2016.
 *
 * Default DiffUtil.callback
 */
public abstract class DefaultDiffCallback<T> extends DiffUtil.Callback {

    private final List<T> oldList;
    private final List<T> newList;

    public DefaultDiffCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return isSameItem(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return hasSameContent(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    public abstract boolean hasSameContent(T oldItem, T newItem);
    
    public abstract boolean isSameItem(T oldItem, T newItem);
}
