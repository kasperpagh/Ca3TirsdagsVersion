'use strict';

var app = angular.module('myApp.view4', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view4', {
                    templateUrl: 'app/view4/view4.html',
                    controller: 'view4Control'
                });
            }]);

app.controller('view4Control', ['$scope', "$http", function ($scope, $http) {
        $scope.linket = "/SemesterSeed/api/currency/dailyrates";
        console.log("asf");
        $scope.rates = [];
        $http(
                {
                    method: "GET",
                    skipAuthorization: true,
                    url: $scope.linket,
                }
        ).then(function (response)
        {

            $scope.rates = response.data.rates;
            console.log($scope.rates[1].currency);
            $scope.refcur = response.data.refcur;
            $scope.dato = response.data.dato;
        });

        $scope.convert = function ()
        {
            console.log("i convo!");
            $scope.amount = $("#amountToConvert").val();
            $scope.fromCur = $("#curFrom").val();
            $scope.toCur = $("#curTo").val();
            $scope.convoUrl = "/SemesterSeed/api/currency/calculator/" + $scope.amount +"/"+$scope.fromCur+"/"+$scope.toCur;
            console.log($scope.convoUrl);
            $http(
                    {
                        method: "GET",
                        skipAuthorization: true,
                        url: $scope.convoUrl,
                    }
            ).then(function (response)
            {
                $("#convertRes").html("DU KAN FÅ: " + response.data + " " + $scope.toCur + ", FOR " + $scope.amount + " " + $scope.fromCur);
            });
        }
    }]);