package work.vuong.template.screen.adduser;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import work.vuong.template.common.model.GithubUser;
import work.vuong.template.databinding.ItemGithubUserBinding;

/**
 * Created by oberon (vuongpham) on 28/10/2016.
 *
 *  Simple adapter to display list of users.
 */
class GithubUserAdapter extends RecyclerView.Adapter<GithubUserAdapter.ViewHolder> {

    private ItemClickListener itemClickListener;
    private List<GithubUser> items;

    GithubUserAdapter(List<GithubUser> items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemGithubUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GithubUser user = items.get(position);
        holder.binding.setUser(user);
        holder.binding.getRoot().setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.onItemClicked(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    List<GithubUser> getItems() {
        return items;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    void setItems(List<GithubUser> items) {
        this.items = items;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemGithubUserBinding binding;

        ViewHolder(ItemGithubUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public interface ItemClickListener {
        void onItemClicked(GithubUser user);
    }
}
