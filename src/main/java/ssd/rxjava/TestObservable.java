package rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

public class TestObservable {

    public static void main(String[] args) {
        Observable.OnSubscribe<Integer> onSubscribe = new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                System.out.println("call.....");
                for (int i = 0; i < 5; i++) {
                    observer.onNext(i);
                }
                observer.onCompleted();
            }
        };
        Observable<Integer> observableString = Observable.create(onSubscribe);
        System.out.println("observableString.....");
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no! Something wrong happened！");
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("Item is " + item);
            }
        };
        Subscription subscriptionPrint = observableString.subscribe(observer);
        System.out.println("isUnsubscribed..." + subscriptionPrint.isUnsubscribed());
    }
}
