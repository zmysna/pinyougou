app.controller('itemCatController', function ($scope, baseService, $controller) {
    /* 指定继承baseController*/
    $controller('baseController', {$scope: $scope});

    /** 根据parentId查询所有的商品分类 */
    $scope.findByParentId = function (parentId) {
        baseService.sendGet("/itemCat/findAllByParentId", "parentId=" + parentId)
            .then(function (response) {
                if (response.data.length > 0) {
                    $scope.dataList = response.data;
                } else {
                    $scope.navigationItems.pop();
                    alert("没有下级分类了");
                }
            });
    };


    /** 导航条面包屑*/
    $scope.navigationItems = [];
    // 添加内容
    $scope.addNavItem = function (itemCat) {
        $scope.navigationItems.push(itemCat);
    };
    // 删除内容
    $scope.removeNavItem = function (index) {
        var num = $scope.navigationItems.length - index;
        $scope.navigationItems.splice(index,num);
    }


    /** 定义brandList和findAllBrands方法*/
    $scope.brandList = {}
    $scope.findAllBrands = function () {
        baseService.sendGet("/brand/findBrandList").then(
            function (response) {
                $scope.brandList.data = response.data;
            }
        )
    };

    /** 定义specificationList和findAllSpecification方法*/
    $scope.specificationList = {}
    $scope.findAllSpecifications = function () {
        baseService.sendGet("/specification/findSpecificationList").then(
            function (response) {
                $scope.specificationList.data = response.data;
            }
        )
    };



});