(function () {
    angular.module('expense-tracker')
        .controller('NavigationController', function ($rootScope, $http, $location) {
            this.isTabActive = function (path) {
                return $location.path() === path;
            };
        })
})();
