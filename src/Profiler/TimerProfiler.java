

class TimerProfiler {
    Account getAccount(AccountKey key) {
        long startTime = System.currentTimeMillis();
        checkAccountPermission(key);
        
        
        Account account = AccountCache.lookupAccount(key);
        
        if (account != null) {
            Profiler.record("getAccount.cached",
                system.currentTimeMillis() -startTime);
            return account;
        }
        account = AccountDao.loadAccount(key);
        AccountCache.putAccount(account);
        Profiler.record("getAccount.loaded",
            system.currentTimeMillis() - startTime);
        return account;
    }
}