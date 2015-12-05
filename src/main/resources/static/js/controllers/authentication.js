(function () {
    angular.module('expense-tracker')
        .controller('AuthenticationController', function ($rootScope, $http, $location) {
            var self = this;
            this.credentials = {};

            var authenticate = function (credentials, callback) {
                var headers = {
                    authorization: "Basic "
                    + btoa(credentials.username + ":" + credentials.password)
                };

                $http.get('/user', {headers: headers}).success(function (data) {
                    $rootScope.authenticated = !!data.name;
                    callback && callback();
                }).error(function () {
                    $rootScope.authenticated = false;
                    callback && callback();
                });

            };

            authenticate(this.credentials);

            this.login = function () {
                authenticate(this.credentials, function () {
                    if ($rootScope.authenticated) {
                        $location.path("/");
                        self.error = false;
                    } else {
                        $location.path("/login");
                        self.error = true;
                    }
                });
            };

            this.logout = function () {
                $http.post('logout', {}).success(function () {
                    $rootScope.authenticated = false;
                    $location.path("/");
                }).error(function () {
                    $rootScope.authenticated = false;
                });
            };

        })
})();
