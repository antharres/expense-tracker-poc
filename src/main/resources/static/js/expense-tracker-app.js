(function () {
    angular.module('expense-tracker', ['expense-tracker-router']).
    directive('appAuth', function(){
        return {
            restrict: 'A',
            templateUrl: '../templates/auth.html',
            controller: 'AuthenticationController',
            controllerAs: 'authCtl'
        }
    });
})();