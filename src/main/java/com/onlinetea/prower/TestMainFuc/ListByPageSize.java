package com.onlinetea.prower.TestMainFuc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListByPageSize {



  public static void main(String[] args) {

      getPageData(1,"Venusd");

  }

  public static List<HostInfo> getInitData(){
      List<HostInfo> pageData = new ArrayList<>();
      for(int i=0;i<3;i++){
          HostInfo info = new HostInfo();
          info.setName("btn-0"+i);
          info.setIp("10.20.3."+i);
          info.setModelName("s500-0"+i);
          info.setTypeName("交换机");
          info.setProducerName("H3C");
          pageData.add(info);
      }
      for(int i=5;i<9;i++){
          HostInfo info = new HostInfo();
          info.setName("stn-0"+i);
          info.setIp("20.20.4."+i);
          info.setModelName("p500-0"+i);
          info.setTypeName("防火墙");
          info.setProducerName("Venusd");
          pageData.add(info);
      }
      for(int i=10;i<15;i++){
          HostInfo info = new HostInfo();
          info.setName("ctn-0"+i);
          info.setIp("30.20.4."+i);
          info.setModelName("p500-0"+i);
          info.setTypeName("防火墙");
          info.setProducerName("Venus");
          pageData.add(info);
      }


      return pageData;
  }

  public static void getPageData(int pageIndex,String keyWord){
      List<HostInfo> pageData =  getInitData();;
      // 13条数据
//      pageData.add("A");
//      pageData.add("B");
//      pageData.add("C");
//      pageData.add("D");
//      pageData.add("E");
//      pageData.add("F");
//      pageData.add("G");
//      pageData.add("H");
//      pageData.add("I");
//      pageData.add("J");
//      pageData.add("K");
//      pageData.add("L");
//      pageData.add("M");
      int total = pageData.size();
      pageData = pageData.stream().filter(x->x.getIp().contains(keyWord)
              ||x.getName().contains(keyWord)
              ||x.getModelName().contains(keyWord)
              ||x.getProducerName().contains(keyWord)
              ||x.getTypeName().contains(keyWord)).collect(Collectors.toList());

      // 当前页数
//      int pageIndex = 1;
      // 一页几条数据
      int pageSize = 10;
      // 总页数
      int pageSum = total%pageSize == 0? total/pageSize:total/pageSize+1;
      //分页
      Set<HostInfo> subList = pageData.stream().skip((pageIndex-1)*pageSize)
              .limit(pageSize).collect(Collectors.toSet());

      System.out.println(subList);
      System.out.println("总数total:"+total);
  }
}
