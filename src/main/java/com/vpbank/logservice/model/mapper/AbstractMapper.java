package com.vpbank.logservice.model.mapper;

public interface AbstractMapper<S, T> {
    T toElasticsearchModel(S s);
}
