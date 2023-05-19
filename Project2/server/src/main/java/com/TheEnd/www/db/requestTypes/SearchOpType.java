package com.TheEnd.www.db.requestTypes;

public enum SearchOpType {
    SearchByHot("SearchByHot"),
    SearchByPostId("SearchByPostId"),
    SearchByUser("SearchByUser"),
    SearchByCategory("SearchByCategory"),
    SearchByLike("SearchByLike"),
    SearchByFavorite("SearchByFavorite"),
    SearchByShared("SearchByShared"),
    SearchDefault("SearchDefault"),
    SearchOpt1("SearchOpt1"),
    SearchOpt2("SearchOpt2"),
    SearchOpt12("SearchOpt12");
    private final String name;

    SearchOpType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
