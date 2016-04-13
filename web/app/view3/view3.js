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

                $scope.productionunits = response.data.productionunits;


                $scope.keys = [];
                $scope.keys = Object.keys($scope.productionunits[1]);
                $scope.prodvalues = [];
                $scope.prodvalues = Object.valueOf()($scope.productionunits[1]);


                $scope.all = [{ key: $scope.keys, prodValue: $scope.prodvalues}];

            }, function (response) {
                console.log(response.data);

            });



});

