<#macro tail type subType="">
  <#-- ===== 引入脚本 start ===== -->
  <#if (type == 'index' && settings.enable_index_list_effect == true) || ( type == 'journals' && settings.enable_journal_effect == true)>
    <script src="${BASE_RES_URL!}/source/lib/wowjs/wow.min.js"></script>
  </#if>
  <script src="${BASE_RES_URL!}/source/lib/lazysizes/lazysizes.min.js"></script>
  <script src="${BASE_RES_URL!}/source/lib/qmsg/qmsg.js"></script>
  <#if settings.show_newreply == true || type == 'sheet'>
    <script src="${BASE_RES_URL}/source/lib/j-marked/marked.min.js"></script>
  </#if>
  <script src="${BASE_RES_URL}/source/js/min/utils.min.js?v=${theme.version!}"></script>
  <#if type == 'index' && settings.enable_banner == true>
    <script src="${BASE_RES_URL!}/source/lib/swiper/swiper.min.js"></script>
  </#if>
  <#if type == 'post' && settings.enable_toc == true>
    <script src="${BASE_RES_URL!}/source/lib/tocbot/tocbot.min.js"></script>
  </#if>
  <#if settings.enable_clean_mode != true && ( type == 'post' || type == 'journals' || type == 'sheet') && subType != 'only_header_footer'>
    <script src="${BASE_RES_URL!}/source/lib/vue@2.6.10/vue.min.js"></script>
    <script src="${BASE_RES_URL!}/source/lib/halo-comment/halo-comment.min.js?v=${theme.version!}"></script>
  </#if>
  <script src="${BASE_RES_URL!}/source/lib/fancybox/jquery.fancybox.min.js"></script>
  <#assign enable_katex = (metas?? && metas.enable_katex?? && metas.enable_katex?trim!='')?then(metas.enable_katex?trim,settings.enable_katex?then('true','false'))>
  <#if enable_katex == 'true' && (type == 'post' || type == 'journals' || type == 'sheet')>
    <link rel="stylesheet" href="${BASE_RES_URL}/source/lib/katex@0.13.18/katex.min.css">
  </#if>
  <script src="${BASE_RES_URL}/source/js/min/custom.min.js?v=${theme.version!}"></script>
  <#if type == 'post' || type == 'journals' || type == 'sheet'>
    <script src="${BASE_RES_URL}/source/lib/clipboard/clipboard.min.js"></script>
  </#if>
  <#if settings.favicon?? && settings.favicon?trim!=''>
    <script src="${BASE_RES_URL}/source/lib/favico/favico.min.js"></script>
  </#if>
  <#if type == 'post'>
    <script src="${BASE_RES_URL}/source/lib/jquery-qrcode/jquery.qrcode.min.js"></script>
  </#if>

  <#-- ===== 引入页面级js start ===== -->
  <script src="${BASE_RES_URL}/source/js/min/common.min.js?v=${theme.version!}"></script>
  <#if type == 'post' || type == 'journals' || type == 'sheet'>
    <script src="${BASE_RES_URL!}/source/lib/prism/prism.min.js"></script>
  </#if>
  <#if type == 'index'>
    <script src="${BASE_RES_URL}/source/js/min/index.min.js?v=${theme.version!}"></script>
  </#if>
  <#if type == 'archives'>
    <script src="${BASE_RES_URL}/source/js/min/archives.min.js?v=${theme.version!}"></script>
  </#if>
  <#if type == 'post'>
    <script src="${BASE_RES_URL}/source/js/min/post.min.js?v=${theme.version!}"></script>
  </#if>
  <#if type == 'journals'>
    <script src="${BASE_RES_URL}/source/js/min/journals.min.js?v=${theme.version!}"></script>
  </#if>
  <#-- ===== 引入页面级js end ===== -->

  <#-- ===== 引入脚本 start ===== -->
  <#if settings.enable_busuanzi!false>
    <!-- 卜算子 -->
    <script src="${BASE_RES_URL}/source/lib/busuanzi/busuanzi.min.js"></script>
  </#if>
  <#-- ===== 引入脚本 end ===== -->

  <#if settings.custom_external_js_body??>
    <!-- 自定义外部js -->
    <script src="${settings.custom_external_js_body!}"></script>
  </#if>
  <#if settings.custom_js_body??>
    <!-- 自定义js -->
    <script type="text/javascript">${settings.custom_js_body!}</script>
  </#if>
  <#if settings.enable_debug>
    <!-- vconsole -->
    <script src="${BASE_RES_URL}/source/lib/vconsole/vconsole.min.js"></script>
  </#if>
  <#if mode != "development">
    <@global.statistics />
  </#if>
</#macro>