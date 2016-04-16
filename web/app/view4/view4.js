'use strict';

var app = angular.module('myApp.view4', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view4', {
                    templateUrl: 'app/view4/view4.html',
                    //her tilføjes kontroller til view4.html :( derfor behøver vi ikke at sige <div ng-controller="johnWhatever">
                    controller: 'view4Control'
                });
            }]);

app.controller('view4Control', ['$scope', "$http", function ($scope, $http) {
        
      
        
        $http({
            method: 'GET',
            url: 'api/demouser'
        }).then(function successCallback(res) {
            $scope.data = res.data.message;
 
            

        

            
        $scope.linket = "/SemesterSeed/api/currency/dailyrates";
        console.log("asf");
        $scope.rates = [];
        $http(
                {
                    method: "GET",
//                    skipAuthorization: true,
                    url: $scope.linket,
                }
        ).then(function (response)
        {
            console.log("Jeg har lavet rest call her er data: !" + response.data);
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
            $scope.convoUrl = "/SemesterSeed/api/currency/calculator/" + $scope.amount + "/" + $scope.fromCur + "/" + $scope.toCur;
            console.log($scope.convoUrl);
            $http(
                    {
                        method: "GET",
//                        skipAuthorization: true,
                        url: $scope.convoUrl,
                    }
            ).then(function (response)
            {
                $("#convertRes").html("DU KAN FÅ: " + response.data + " " + $scope.toCur + ", FOR " + $scope.amount + " " + $scope.fromCur);
            });
        }
                }, function errorCallback(res) {
            $scope.error = res.status + ": " + res.data.statusText;
            window.location.href = '/SemesterSeed/#/view1';
        });
    }]);