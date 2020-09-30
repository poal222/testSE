package com.example;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.function.LongConsumer;

public class  Test {

	public static void main(String[] args) {
		Flux flux = Flux.create(sink -> {
			//向下游发布元素
			sink.next("helloword");
			sink.next("helloword2");
			sink.next("helloword3");

			//结束发布元素
			sink.complete();
			//一次性
			System.out.println(sink.currentContext());
			sink.isCancelled();
			sink.onRequest(value -> System.out.println(value));
			sink.onCancel(() -> System.out.println("woshi onCancel"));
			sink.onDispose(() -> System.out.println("woshi Disposable"));

		});

//		flux.next().subscribe(System.out::print);

		flux.subscribe(System.out::println);//subscribe发布消息，System.out.println为消费者，消费消息;

	}

}