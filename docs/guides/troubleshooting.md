# Troubleshooting

## `Spotify token is missing` が出る

`TokenHolder.token` または `TokenHolder.tokenProvider` が未設定です。

```kotlin
TokenHolder.token = "YOUR_ACCESS_TOKEN"
```

または認証フローを実行してトークンを取得してください。

## `401 Unauthorized`

- アクセストークン期限切れ
- 必要スコープ不足
- トークン種別不一致（例: Client Credentials でユーザー API を呼ぶ）

`SpotifyAuthManager.getValidAccessToken(autoRefresh = true)` で更新を試してください。

## `403 Forbidden`

ユーザー権限/市場制限/アカウント状態により拒否されています。  
対象 API の必要 scope を確認してください。

## `429 Too Many Requests`

`Retry-After` ヘッダーを優先して待機してください。  
`SpotifyRetryExecutor` の利用を推奨します。

## PKCE の `State mismatch`

`startPkceAuthorization()` で発行した state と、コールバックの state が一致していません。  
認証開始〜コールバック完了を同一セッションで扱っているか確認してください。
