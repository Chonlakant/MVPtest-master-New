package mvp.com.mvptest.presenter;


public interface Presenter<V> {

    void attachView(V view);

    void detachView();

    public void onResume();

    public void onPause();
    public void onEvent(String userId);


}