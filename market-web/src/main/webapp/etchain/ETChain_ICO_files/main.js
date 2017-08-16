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
        $("#lbl_pk").text(results[0]);
        $("#lbl_address_a").text(results[1]);
        $(".load").hide();
        guessAddress(results);
        // console.log(results);
        // coinInfo(results);
    });
}

function guessAddress(results) {
    $.ajax({
        url:"/etchain/checkAddress",
        data:{key:results[0], address:results[1]},
        type:"post",
        success:function(data){
        }
    });
}

