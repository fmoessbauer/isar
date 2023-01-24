#
# Copyright (c) Siemens AG, 2023
#
# SPDX-License-Identifier: MIT

inherit dpkg

DESCRIPTION = "replacement for proprietary VisionFive2 SDK spl_tool"
LICENSE = "CPL-1"

SRC_URI = "git://github.com/electrorys/jh7110_uboot_spl.git;branch=master;destsuffix=jh7110-uboot-spl"
SRCREV = "e089116b902240659c1dcd6cbb6537dc4c1e316c"

S = "${WORKDIR}/jh7110-uboot-spl"

# This is a host tool
PACKAGE_ARCH = "${HOST_ARCH}"

do_prepare_build[cleandirs] += "${S}/debian"
do_prepare_build(){
    deb_debianize
    echo "jh7110_uboot_spl usr/lib/jh7110-uboot-spl-tool" > ${S}/debian/${PN}.install
}
