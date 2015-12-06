(function () {
    angular.module('expense-tracker-router', ['ngRoute'])
        .config(function ($routeProvider, $httpProvider) {
            $routeProvider.when('/', {
                templateUrl: '../templates/greeting.html',
                controller: 'GreetingController',
                controllerAs: 'greetingCtl'
            }).when('/account/', {
                templateUrl: '../templates/account.html',
                controller: 'AccountController',
                controllerAs: 'accountCtl'
            }).otherwise('/');

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        });
})();