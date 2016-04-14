'use strict';

var app = angular.module('myApp.view3', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view3', {
                    templateUrl: 'app/view3/view3.html',
                });
            }]);



app.controller('View3Ctrl', function ($scope, $http)
{
    $scope.oneAtATime = true;
    $http({method: 'GET', url: 'http://cvrapi.dk/api?vat=3167%208021&country=dk',
        skipAuthorization: true})
            .then(function (response) {

                $scope.firmName = response.data.name;
                $scope.firm = response.data;
                console.log($scope.firmName);
                $scope.productionunits = response.data.productionunits;




            });



});

app.filter('myFilter', function () {

    return function (obj) {
            var a = {};
        angular.forEach(obj, function(value, key) {
            if (key !== "productionunits") {
                a[key] = value;
            }
            });
            return a;
         
         
    };
});
