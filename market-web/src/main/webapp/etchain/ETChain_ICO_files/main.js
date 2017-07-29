//创建小蚁账户的按钮
function create_antshares_account()
{
    createAccount();
};

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
        //$(".load").hide();
        var account = {};
        account.key = results[0];
        account.key = results[1];
        return account;
    });
}

