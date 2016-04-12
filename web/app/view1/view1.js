'use strict';

angular.module('myApp.view1', ['ngRoute'])

        .config(['$routeProvider', function ($routeProvider) {
                $routeProvider.when('/view1', {
                    templateUrl: 'app/view1/view1.html',
                    controller: 'View1Ctrl',
                    controllerAs: 'ctrl'
                });
            }])

        .controller('View1Ctrl',  ["InfoFactory", "InfoService","$http", function (InfoFactory, InfoService,$http) {
                this.msgFromFactory = InfoFactory.getInfo();
                this.msgFromService = InfoService.getInfo();
                this.youAreAUser = false;
                this.makeUser = function ()
                {
                    
                    this.userName = $("#desiredUsername").val();
                    this.passWord = $("#desiredPassword").val();
                    this.data = {username: this.userName, password: this.passWord};
                    this.dataToSend = angular.toJson(this.data, true);
                    console.log(this.dataToSend);

                    $http.post("/SemesterSeed/api/demouser", this.dataToSend).success(function (data)
                    {
                        this.youAreAUser = true;
                    });
                };
            }]);