package pl.training.search;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Predicate;
import lombok.extern.java.Log;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.reactivex.rxjava3.core.Observable.zip;
import static java.lang.Runtime.getRuntime;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;

@Log
public class Main {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final GithubApi githubApi = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(JacksonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(new OkHttpClient()
                    .newBuilder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(BASIC))
                    .build())
            .build()
            .create(GithubApi.class);

    private Predicate<String> byMinLength(int length) {
        return text -> text.length() >= length;
    }

    private <E> List<E> concat(List<E> list, List<E> otherList) {
        var result = new ArrayList<>(list);
        result.addAll(otherList);
        return result;
    }

    private Observable<List<String>> getRepositoriesNames(String query) {
        return githubApi.get(query)
                .map(QueryResultDto::getItems)
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(RepositoryDto::getName)
                .toSortedList()
                .toObservable();
    }

    private Observable<List<String>> zipRequests(String text) {
        return zip(getRepositoriesNames(text.toLowerCase()), getRepositoriesNames(text.toUpperCase()), this::concat);
    }


    private void start() {
        getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Disposing disposables");
            compositeDisposable.dispose();
        }));

        var disposable = ObservableInputStream.fromInputStream(System.in)
                .debounce(5, TimeUnit.SECONDS)
                .flatMap(this::zipRequests)
                .subscribe(result -> log.info(result.toString()), throwable -> log.info(throwable.toString()));

        compositeDisposable.add(disposable);

       /* getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Disposing disposables");
            compositeDisposable.dispose();
        }));*/

        /*githubApi.get("java")
                .map(QueryResultDto::getItems)
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(RepositoryDto::getName)
                .map(String::toLowerCase)
                .filter(byMinLength(3))
                .sorted()
                .distinct()
                .subscribe(result -> log.info(result.toString()), throwable -> log.info(throwable.toString()));*/

  /*      var disposable = fromInputStream(System.in)
                .subscribeOn(Schedulers.io())
                .debounce(5, TimeUnit.SECONDS)
                .flatMap(text ->  zip(getRepositoriesNames(text.toLowerCase()), getRepositoriesNames(text.toUpperCase()), this::concat))
                //.flatMap(this::getRepositoriesNames)
                .subscribe(result -> log.info(result.toString()), throwable -> log.info(throwable.toString()));

        compositeDisposable.add(disposable);*/
    }

    public static void main(String[] args) throws InterruptedException {
        new Main().start();
        log.info("Before sleep");
        Thread.sleep(10_000);
        log.info("Before shutdown");
    }

}
