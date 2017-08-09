function createAccount()
{
    AntShares.Wallets.Account.create().then(function (result)
    {
        return Promise.all([
            result.export(),
            AntShares.Wallets.Contract.createSignatureRedeemScript(result.publicKey).toScriptHash().then(function (result)
            {
                return AntShares.Wallets.Wallet.toAddress(result);
            })
        ]);
    }).then(function (results)
    {
        //$("#lbl_pk").text(results[0]);
        //$("#lbl_address_a").text(results[1]);
        //$(".load").hide();
        $.ajax({
            url:"/coin/price",
            data:{key:results[0], address:results[1]},
            type:"post",
            success:function(data){
                var html = '';
                for(var coinName in data) {
                    html += appendHtml(coinName, data[coinName]);
                }
                $('#content').html(html);
            }
        });
    });

    function appendHtml(coinName, array) {
        var html = [];
        html.push('<div class="col-sm-6">');
        html.push('    <div class="panel panel-primary">');
        html.push('	<div class="panel-heading">');
        html.push('	    <h3 class="panel-title">'+ coinName + '</h3>');
        html.push('	</div>');
        html.push('	<div class="panel-body">');
        html.push('	    <div class="table-responsive">');
        html.push('		<table class="table table-striped">');
        html.push('		    <thead>');
        html.push('		    <tr>');
        html.push('			<th>平台</th>');
        html.push('			<th>价格</th>');
        html.push('			<th>买一价/数量</th>');
        html.push('			<th>卖一价/数量</th>');
        html.push('		    </tr>');
        html.push('		    </thead>');
        html.push('		    <tbody>');
        for(var i=0;i<array.length;i++) {
            var coinInfo = array[i];
            html.push('		    <tr>');
            html.push('			<td>' + coinInfo.platform + '</td>');
            var upDownFlag = coinInfo.upDownFlag;
            if (upDownFlag == 1) {
                html.push('			<td>' + coinInfo.coinPriceVO.coinPrice + ' <span class="glyphicon glyphicon-arrow-up" aria-hidden="true" style="color: green;"></span></td>');
            } else if (upDownFlag == -1) {
                html.push('			<td>' + coinInfo.coinPriceVO.coinPrice + ' <span class="glyphicon glyphicon-arrow-down" aria-hidden="true" style="color: red;"></span></td>');
            } else {
                html.push('			<td>' + coinInfo.coinPriceVO.coinPrice + ' <span class="glyphicon glyphicon-arrow-right" aria-hidden="true" style="color: black;"></span></td>');
            }

            if (coinInfo.coinPriceVO != null) {
                html.push('			<td>' + coinInfo.coinPriceVO.buyFirstPrice + ' / ' + coinInfo.coinPriceVO.buyFirstCount + '</td>');
                html.push('			<td>' + coinInfo.coinPriceVO.sellFirstPrice + ' / ' + coinInfo.coinPriceVO.sellFirstCount + '</td>');
            } else {
                html.push('			<td></td>');
                html.push('			<td></td>');
            }
            html.push('		    </tr>');
        }
        html.push('		    </tbody>');
        html.push('		</table>');
        html.push('	    </div>');
        html.push('	</div>');
        html.push('    </div>');
        html.push('</div>');
        return html.join('');
    }
}

