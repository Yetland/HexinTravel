package com.ynchinamobile.hexinlvxing.core.base

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import rx.subjects.Subject
import java.util.concurrent.ConcurrentHashMap

/**
 * @Name:           RxBus
 * @Author:         yeliang
 * @Date:           2017/7/6
 */
open class RxBus {

    private var instance: RxBus? = null

    @Synchronized
    open fun newInstance(): RxBus {
        if (null == instance) {
            instance = RxBus()
        }
        return instance as RxBus
    }


    private val subjectMapper = ConcurrentHashMap<String, MutableList<Subject<*, *>>>()

    fun onEvent(observable: Observable<*>, action1: Action1<Any>): RxBus {
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
                .subscribe(action1, Action1<Throwable> { it.printStackTrace() })
        return newInstance()
    }


    fun <T> register(tag: String): Observable<T> {
        val subjectList: MutableList<Subject<*, *>>? = subjectMapper[tag]

        subjectList?.let { subjectMapper.put(tag, it) }

        val subject: Subject<T, T> = PublishSubject.create()
        subjectList!!.add(subject)
        return subject
    }

    fun unregister(tag: Any, observable: Observable<*>?): RxBus {
        if (observable == null) {
            return newInstance()
        }
        val subjects = subjectMapper[tag]
        if (null != subjects) {
            subjects.remove(observable)
            if (!isEmpty(subjects)) {
                subjectMapper.remove(tag)
            }
        }
        return newInstance()
    }


    fun post(tag: Any, content: Any) {
        val subjectList = subjectMapper[tag]
        if (subjectList != null) {
            for (subject in subjectList) {
            }
        }
    }

    fun isEmpty(collection: Collection<Subject<*, *>>?): Boolean {
        return null == collection || collection.isEmpty()
    }
}