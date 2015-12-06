(function () {
    angular.module('expense-tracker')
        .controller('AuthenticationController', function ($rootScope, $http, $location) {
            var self = this;
            this.credentials = {};
            this.authenticatedUser = {};
            $rootScope.authenticated = false;

            var authenticate = function (credentials, callback) {
                var headers = credentials ? {
                    authorization: "Basic "
                    + btoa(credentials.username + ":" + credentials.password)
                } : {};

                $http.get('/user', {headers: headers}).success(function (data) {
                    $rootScope.authenticated = !!data.name;
                    self.authenticatedUser = {
                        name: data.name
                    };
                    callback && callback();
                }).error(function () {
                    $rootScope.authenticated = false;
                    callback && callback();
                });

            };

            authenticate();

            this.login = function () {
                authenticate(this.credentials, function () {
                    $location.path("/");
                    self.error = !$rootScope.authenticated;
                });
            };

            this.logout = function () {
                self.authenticatedUser = {};
                self.credentials = {};
                $http.post('logout', {}).success(function () {
                    $rootScope.authenticated = false;
                    $location.path("/");
                }).error(function () {
                    $rootScope.authenticated = false;
                });
            };

        })
})();
