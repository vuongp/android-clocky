package work.vuong.template.common.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vuongp on 14-10-16.
 *
 * A github search response object using generics to have the results be able to be any Object.
 *  // TODO: 14-10-16 Add T extends GithubObject to ensure other classes can't be used.
 */
public class GithubSearch<T> {

    @SerializedName("total_count") private int totalCount;
    @SerializedName("incomplete_results") private boolean isIncomplete;
    @SerializedName("items") private List<T> results;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isIncomplete() {
        return isIncomplete;
    }

    public void setIncomplete(boolean incomplete) {
        isIncomplete = incomplete;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public static <T> GithubSearch<T> empty(Class<T> clazz) {
        GithubSearch<T> githubSearch = new GithubSearch<>();
        githubSearch.setResults(new ArrayList<>());
        return githubSearch;
    }
}
