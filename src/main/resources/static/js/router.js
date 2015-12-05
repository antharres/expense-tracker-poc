(function () {
    angular.module('expense-tracker-router', ['ngRoute'])
        .config(function ($routeProvider, $httpProvider) {
            $routeProvider.when('/', {
                templateUrl: '../templates/home.html',
                controller: 'HomeController',
                controllerAs: 'homeCtl'
            }).when('/login', {
                templateUrl: '../templates/login.html',
                controller: 'AuthenticationController',
                controllerAs: 'authCtl'
            }).otherwise('/');

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        });
})();