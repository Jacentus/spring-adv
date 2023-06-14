package pl.training.search;

import io.reactivex.rxjava3.core.Observable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ObservableInputStream {

    public static Observable<String> fromInputStream(InputStream inputStream) {
        return Observable.create(emitter -> {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String text;
                while ((text = bufferedReader.readLine()) != null) {
                    emitter.onNext(text);
                }
            } catch (Exception exception) {
                emitter.onError(exception);
            }
            finally {
                emitter.onComplete();
            }
        });
    }

}
