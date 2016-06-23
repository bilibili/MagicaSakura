-keepnames class android.support.v7.widget.RecyclerView{
    android.support.v7.widget.RecyclerView$Recycler mRecycler;
}

-keepnames class android.support.v7.widget.RecyclerView$Recycler{
    public void clear();
}