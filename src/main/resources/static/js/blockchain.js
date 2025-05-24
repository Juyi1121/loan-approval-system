// 前端呼叫後端審核 & 顯示 Tx 連結
async function approve(id){
    const res = await fetch(`/api/applications/${id}/approve`, { method:'PATCH' });
    const j   = await res.json();
    alert(res.ok ? `已核准 (Tx: ${j.blockchainTxId||'PENDING'})` : '核准失敗');
    location.reload();
  }
  