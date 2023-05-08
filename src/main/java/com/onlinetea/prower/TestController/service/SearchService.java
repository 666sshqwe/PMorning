package com.onlinetea.prower.TestController.service;

import java.io.IOException;

public interface SearchService {

    void analysiStory(String url,String Menu,String requestID,String storyName);

    void loadAllContent(String url) throws IOException, InterruptedException;

}
