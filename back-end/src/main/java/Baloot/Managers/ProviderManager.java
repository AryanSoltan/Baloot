//package Baloot.Managers;
//
//import Baloot.Commodity;
//import Baloot.Exception.ProviderNotExist;
//import Baloot.Provider;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ProviderManager {
//    Map<Integer, Provider> providers;
//
//    public ProviderManager()
//    {
//        providers = new HashMap<Integer,Provider>() ;
//    }
//    public void addNewProvider(Provider newProvider)
//    {
//        int id = newProvider.getId();
//        newProvider.setCommoditiesEmpty();
//        providers.put(id, newProvider);
//    }
//
//    public Provider getProviderByID(int providerId) throws Exception
//    {
//        if(providers.containsKey(providerId))
//            return providers.get(providerId);
//        else
//            throw new ProviderNotExist(providerId);
//    }
//
//    public void addNewCommodity(Commodity neededCommodity, Provider provider)
//    {
//        provider.addCommodity(neededCommodity);
//    }
//
//}
