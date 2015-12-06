(function () {
    angular.module('expense-tracker-router', ['ngRoute'])
        .config(function ($routeProvider, $httpProvider) {
            $routeProvider.when('/', {
                templateUrl: '../templates/home.html',
                controller: 'HomeController',
                controllerAs: 'homeCtl'
            }).otherwise('/');

            $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

        });
})();