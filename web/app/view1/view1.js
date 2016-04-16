'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View1Ctrl',  ["InfoFactory", "InfoService","$http", "$scope", function (InfoFactory, InfoService,$http,$scope) {
                this.msgFromFactory = InfoFactory.getInfo();
                this.msgFromService = InfoService.getInfo();
                $scope.youAreAUser = false;
                this.makeUser = function ()
                {
                    this.userName = $("#desiredUsername").val();
                    this.passWord = $("#desiredPassword").val();
                    this.data = {username: this.userName, password: this.passWord};
                    this.dataToSend = angular.toJson(this.data, true);
                    console.log(this.dataToSend);

                    $http.post("/SemesterSeed/api/createuser", this.dataToSend).success(function (data)
                    {
                        console.log("Du er nu oprettet!");
                        $scope.youAreAUser = true;
                    }).error(function()
                    {
                        $("#status").html("<h3>DER ER SKET EN FEJL, PRØV IGEN!</h3>");
                    });
                    };
        }]);