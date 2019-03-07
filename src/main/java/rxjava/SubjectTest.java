package rxjava;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class SubjectTest {

    public static void main(String[] args) {

        // replaySubjectTest();

        Observable.just("Hello, world!")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println(s);
                    }
                });


    }


    private static void publishSubjectTest() {
        PublishSubject<String> stringPublishSubject = PublishSubject.create();
        Subscription subscriptionPrint = stringPublishSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no!Something wrong happened!");
            }

            @Override
            public void onNext(String message) {
                System.out.println(message);
            }
        });
        stringPublishSubject.onNext("Hello World");
    }

    private static void publishSubjectTest2() {
        final PublishSubject<Boolean> subject = PublishSubject.create();

        subject.subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                System.out.println("Observable Completed " + aBoolean);
            }
        });

        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).doOnCompleted(new Action0() {
            @Override
            public void call() {
                subject.onNext(true);
            }
        }).subscribe();
    }


    private static void behaviorSubjectTest() {
        BehaviorSubject<Integer> behaviorSubject = BehaviorSubject.create(1);
        Subscription subscriptionPrint = behaviorSubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no!Something wrong happened!");
            }

            @Override
            public void onNext(Integer message) {
                System.out.println(message);
            }
        });
    }


    private static void replaySubjectTest() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create(1);
        Subscription subscriptionPrint1 = replaySubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no!Something wrong happened!");
            }

            @Override
            public void onNext(Integer message) {
                System.out.println("1===" + message);
            }
        });
        replaySubject.onNext(12);
        replaySubject.onNext(13);
        replaySubject.onNext(14);
        Subscription subscriptionPrint2 = replaySubject.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                System.out.println("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Oh,no!Something wrong happened!");
            }

            @Override
            public void onNext(Integer message) {
                System.out.println("2====" + message);
            }
        });
    }
}
